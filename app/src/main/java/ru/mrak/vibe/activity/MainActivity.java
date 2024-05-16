package ru.mrak.vibe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import ru.mrak.vibe.R;
import ru.mrak.vibe.model.User;
import ru.mrak.vibe.util.UserName;
import ru.mrak.vibe.util.codeGenerator.CodeGenerator;
import ru.mrak.vibe.util.codeGenerator.CycleCode;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            signUp();
        } else {
            Log.d(TAG, "user already has, user uid: " + currentUser.getUid());
            database.getReference("users").child(currentUser.getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "user name: " + task.getResult().getValue(User.class).name);
                    toVibeListActivity();
                } else {
                    Log.d(TAG, "user don't have");
                }
            });
        }
    }

    private void signUp() {
        Log.d(TAG, "signUp");
        CompletableFuture.runAsync(() -> {
            FirebaseUser firebaseUser = signUpAnonymously();
            CodeGeneratorSettings codeGeneratorSettings = getCodeGeneratorSettings();
            String userName = getUserName(codeGeneratorSettings.step, codeGeneratorSettings.length);

            User user = new User(firebaseUser.getUid(), userName);
            writeUserData(user);
            toVibeListActivity();
        });
    }

    private void writeUserData(User user) {
        Log.d(TAG, "writeUserData");
        database.getReference("users").child(user.uid).setValue(user);
    }

    private static class UserNameWrapper {
        List<String> animals;
        List<String> personalities;
        Integer userCount;
        boolean error = false;
    }

    private static class CodeGeneratorSettings {
        int step;
        int length;

        boolean error = false;
    }

    private CodeGeneratorSettings getCodeGeneratorSettings() {
        Log.d(TAG, "getCodeGeneratorSettings");
        CodeGeneratorSettings result = new CodeGeneratorSettings();
        CountDownLatch latch = new CountDownLatch(2);

        database.getReference("user_name/step").get().addOnCompleteListener(task2 -> {
            if (task2.isSuccessful()) {
                result.step = task2.getResult().getValue(Integer.class);
            } else {
                result.error = true;
            }
            latch.countDown();
            Log.d(TAG, "getCodeGeneratorSettings: step finish");
        });

        database.getReference("user_name/length").get().addOnCompleteListener(task2 -> {
            if (task2.isSuccessful()) {
                result.length = task2.getResult().getValue(Integer.class);
            } else {
                result.error = true;
            }
            latch.countDown();
            Log.d(TAG, "getCodeGeneratorSettings: length finish");
        });

        try {
            Log.d(TAG, "getCodeGeneratorSettings: before wait");
            latch.await();
            Log.d(TAG, "getCodeGeneratorSettings: after wait");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private String getUserName(int step, int length) {
        Log.d(TAG, "getUserName: start");
        CountDownLatch latch = new CountDownLatch(3);
        UserNameWrapper userNameWrapper = new UserNameWrapper();
        CodeGenerator codeGenerator = new CycleCode(step, length);

        database.getReference("user_name/code_value").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                Log.d(TAG, "getUserName: doTransaction");
                userNameWrapper.userCount = currentData.getValue(Integer.class);
                currentData.setValue(userNameWrapper.userCount != null ? codeGenerator.getCode(userNameWrapper.userCount) : 0);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                Log.d(TAG, "getUserName: onComplete");
                if (currentData == null) {
                    userNameWrapper.error = true;
                    Log.d(TAG, "onComplete: error: " + error);
                }
                latch.countDown();
            }
        });

        database.getReference("user_name/animals").get().addOnCompleteListener(task2 -> {
            if (task2.isSuccessful()) {
                userNameWrapper.animals = (List<String>) task2.getResult().getValue();
            } else {
                userNameWrapper.error = true;
            }
            latch.countDown();
            Log.d(TAG, "getUserName: finish animals");
        });

        database.getReference("user_name/personality").get().addOnCompleteListener(task2 -> {
            if (task2.isSuccessful()) {
                userNameWrapper.personalities = (List<String>) task2.getResult().getValue();
            } else {
                userNameWrapper.error = true;
            }
            latch.countDown();
            Log.d(TAG, "getUserName: finish personality");
        });

        try {
            Log.d(TAG, "getUserName: before wait");
            latch.await();
            return UserName.getUserName(
                    userNameWrapper.userCount,
                    userNameWrapper.animals,
                    userNameWrapper.personalities
            );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private FirebaseUser signUpAnonymously() {
        Log.d(TAG, "signUpAnonymously");
        CountDownLatch latch = new CountDownLatch(1);
        Object[] result = new Object[1];

        auth.signInAnonymously()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInAnonymously:success");
                        result[0] = auth.getCurrentUser();
                    } else {
                        Log.d(TAG, "signInAnonymously:failure", task.getException());
                    }
                    latch.countDown();
                });

        try {
            latch.await();
            return (FirebaseUser) result[0];
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void toVibeListActivity() {
        Intent intent = new Intent(this, VibeListActivity.class);
        this.startActivity(intent);
    }
}
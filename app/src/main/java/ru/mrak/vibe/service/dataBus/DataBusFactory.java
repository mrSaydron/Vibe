package ru.mrak.vibe.service.dataBus;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.mrak.vibe.service.objectMapper.ObjectMapperFactory;

public class DataBusFactory {

    private static DataBusServiceImpl dataBusService;

    public static DataBusService getDataBusService() {
        if (dataBusService == null) {
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            dataBusService = new DataBusServiceImpl(objectMapper);
        }
        return dataBusService;
    }
}

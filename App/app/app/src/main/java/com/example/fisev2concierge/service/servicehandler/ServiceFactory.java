package com.example.fisev2concierge.service.servicehandler;

// Employs factory design pattern for the servicesAPI.serviceHandler.ServiceModel
public class ServiceFactory {
    private static ServiceModel serviceModel;

    public static ServiceModel instance() {
        if (serviceModel == null)
            serviceModel = new ServiceModel();
        return serviceModel;
    }
}

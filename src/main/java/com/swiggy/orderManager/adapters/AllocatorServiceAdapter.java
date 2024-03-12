package com.swiggy.orderManager.adapters;

import com.swiggy.orderManager.dtos.DeliveryAgentDto;
import deliveryAgentAllocator.AllocatorRequest;
import deliveryAgentAllocator.AllocatorResponse;
import deliveryAgentAllocator.DeliveryAgentAllocatorServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class AllocatorServiceAdapter {
    private static final int CURRENCY_CONVERTOR_SERVICE_PORT = 8090;
    private static final String CURRENCY_CONVERTOR_SERVICE_HOST = "localhost";

    private static final ManagedChannel CHANNEL = ManagedChannelBuilder
            .forAddress(CURRENCY_CONVERTOR_SERVICE_HOST, CURRENCY_CONVERTOR_SERVICE_PORT)
            .usePlaintext()
            .build();

    private static final DeliveryAgentAllocatorServiceGrpc.DeliveryAgentAllocatorServiceBlockingStub CLIENT = DeliveryAgentAllocatorServiceGrpc.newBlockingStub(CHANNEL);
    public DeliveryAgentDto allocate(int deliveryLocationPincode){
        AllocatorRequest request = AllocatorRequest.newBuilder().setDeliveryLocationPincode(deliveryLocationPincode).build();
        AllocatorResponse response = CLIENT.allocate(request);
        return new DeliveryAgentDto(response.getDeliveryAgent().getId(), response.getDeliveryAgent().getUserId(), response.getDeliveryAgent().getCurrentLocationPincode());
    }
}

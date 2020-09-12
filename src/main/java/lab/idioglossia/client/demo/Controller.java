package lab.idioglossia.client.demo;

import lab.idioglossia.row.client.RowClient;
import lab.idioglossia.row.client.RowClientFactory;
import lab.idioglossia.row.client.Subscription;
import lab.idioglossia.row.client.callback.ResponseCallback;
import lab.idioglossia.row.client.callback.SubscriptionListener;
import lab.idioglossia.row.client.model.RowRequest;
import lab.idioglossia.row.client.model.RowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
public class Controller {
    private final RowClient rowClient;
    private final RowClientFactory rowClientFactory;

    @Autowired
    public Controller(RowClient rowClient, RowClientFactory rowClientFactory) {
        this.rowClient = rowClient;
        this.rowClientFactory = rowClientFactory;
    }

    //t1
    @GetMapping("/send/1")
    public void send1() throws IOException {
        RowRequest<SampleDto, SampleDto> request = RowRequest.<SampleDto, SampleDto>builder()
                .address("/t1")
                .method(RowRequest.RowMethod.GET)
                .build();
        rowClient.sendRequest(request, new ResponseCallback<SampleDto>(SampleDto.class) {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse.toString());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    //t2
    @GetMapping("/send/2")
    public void send2() throws IOException {
        RowRequest<SampleDto, SampleDto> request = RowRequest.<SampleDto, SampleDto>builder()
                .address("/t2")
                .method(RowRequest.RowMethod.POST)
                .body(new SampleDto("alter me :P "))
                .build();
        rowClient.sendRequest(request, new ResponseCallback<SampleDto>(SampleDto.class) {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse.toString());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    //t3
    @GetMapping("/send/3")
    public void send3() throws IOException {
        RowRequest<SampleDto, SampleDto> request = RowRequest.<SampleDto, SampleDto>builder()
                .address("/t3")
                .method(RowRequest.RowMethod.POST)
                .body(new SampleDto("alter me :P "))
                .query(new SampleDto("This is my query"))
                .build();
        rowClient.sendRequest(request, new ResponseCallback<SampleDto>(SampleDto.class) {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse.toString());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    //t4
    @GetMapping("/send/4")
    public void send4() throws IOException {
        RowRequest<SampleDto, SampleDto> request = RowRequest.<SampleDto, SampleDto>builder()
                .address("/t4/hello")
                .method(RowRequest.RowMethod.GET)
                .build();
        rowClient.sendRequest(request, new ResponseCallback<SampleDto>(SampleDto.class) {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse.toString());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    //subscribe
    @GetMapping("/send/5")
    public void send5() throws IOException {
        RowRequest<SampleDto, SampleDto> request = RowRequest.<SampleDto, SampleDto>builder()
                .address("/subs/t1")
                .method(RowRequest.RowMethod.GET)
                .build();

        rowClient.subscribe(request, new ResponseCallback<SampleDto>(SampleDto.class) {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse);
                System.out.println(rowResponse.getSubscription());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        }, new SubscriptionListener<SampleDto>(SampleDto.class) {
            @Override
            public void onMessage(Subscription subscription, SampleDto sampleDto) {
                System.out.println("Received published message-> " + sampleDto);
                System.out.println(subscription);
                subscription.close(new ResponseCallback<SampleDto>(SampleDto.class) {
                    @Override
                    public void onResponse(RowResponse<SampleDto> rowResponse) {
                        System.out.println(rowResponse);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();

                    }
                });
            }
        });
    }

    //publish
    @GetMapping("/send/6")
    public void send6() throws IOException {
        RowRequest<SampleDto, SampleDto> request = RowRequest.<SampleDto, SampleDto>builder()
                .address("/subs/publish/t1")
                .method(RowRequest.RowMethod.POST)
                .body(new SampleDto("A published messaged with id: "+ UUID.randomUUID().toString()))
                .build();
        rowClient.sendRequest(request, new ResponseCallback<SampleDto>(SampleDto.class) {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse.toString());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    //Example to create new row client from factory
    @GetMapping("/send/n")
    public void sendN() throws IOException {
        RowClient rowClient = rowClientFactory.getRowClient("ws://localhost:8080/ws");
        rowClient.open();
        RowRequest<SampleDto, SampleDto> request = RowRequest.<SampleDto, SampleDto>builder()
                .address("/t1")
                .method(RowRequest.RowMethod.GET)
                .build();
        rowClient.sendRequest(request, new ResponseCallback<SampleDto>(SampleDto.class) {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse.getBody().toString());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}

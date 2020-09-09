package lab.idioglossia.client.demo;

import lab.idioglossia.row.client.RowClient;
import lab.idioglossia.row.client.RowClientFactory;
import lab.idioglossia.row.client.callback.ResponseCallback;
import lab.idioglossia.row.client.model.RowRequest;
import lab.idioglossia.row.client.model.RowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controller {
    private final RowClient rowClient;
    private final RowClientFactory rowClientFactory;

    @Autowired
    public Controller(RowClient rowClient, RowClientFactory rowClientFactory) {
        this.rowClient = rowClient;
        this.rowClientFactory = rowClientFactory;
    }

    @GetMapping("/send/1")
    public void send1() throws IOException {
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

    @GetMapping("/send/2")
    public void send2() throws IOException {
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

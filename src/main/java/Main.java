

import com.chibisovap.console.Console;
import com.chibisovap.console.Iinput;
import com.chibisovap.data.CacheData;
import com.chibisovap.data.CacheDataImpl;
import com.chibisovap.models.ApiResponse;

import java.time.LocalDate;

import static com.chibisovap.connection.ConnectionService.getRequest;
import static com.chibisovap.parser.JsonParser.parseJsonToApiResponse;

public class Main {

    public static void main(String[] args) {

        Iinput console = new Console();
        CacheData cacheData = new CacheDataImpl();

        Thread threadLoadCache = new Thread() {
            @Override
            public void run() {
                cacheData.loadFromFile();
            }
        };
        threadLoadCache.start();

        ProgressBar progressBar = new ProgressBar();
        String result;
        if (console.getNewInput()) {
            progressBar.outProgress();

            if(cacheData.isActualCache(LocalDate.now())) {
                result = cacheData.getValue(console.getFrom(), console.getTo());
                progressBar.outProgress();
                if (result != null) {
                    ApiResponse apiResponse = parseJsonToApiResponse(getRequest(console.getFrom(), console.getTo()));
                    result = apiResponse.toString();
                    progressBar.outProgress();
                }
            } else {
                ApiResponse apiResponse = parseJsonToApiResponse(getRequest(console.getFrom(), console.getTo()));
                result = apiResponse.toString();
                System.out.println("\r"+ result);
            }
            Thread threadSaveCache = new Thread() {
                @Override
                public void run() {
                    cacheData.saveToFile();
                    progressBar.outProgress();
                }
            };
            threadSaveCache.start();
            System.out.print(result);
        }

    }

}

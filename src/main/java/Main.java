

import com.chibisovap.console.Console;
import com.chibisovap.console.Iinput;
import com.chibisovap.console.progressBar.IProgressBar;
import com.chibisovap.data.CacheData;
import com.chibisovap.data.CacheDataImpl;
import com.chibisovap.models.ApiResponse;
import com.chibisovap.console.progressBar.ProgressBar;

import java.time.LocalDate;

import static com.chibisovap.connection.ConnectionService.getRequest;
import static com.chibisovap.parser.JsonParser.parseJsonToApiResponse;

public class Main {

    public static void main(String[] args) {

        Iinput console = new Console();
        CacheData cacheData = new CacheDataImpl();
        IProgressBar progressBar = new ProgressBar();

        Thread threadLoadCache = new Thread() {
            @Override
            public void run() {
                cacheData.loadFromFile();
            }
        };
        threadLoadCache.start();


        String result;
        while (true) {
            result = null;
            if (console.getNewInput()) {
                progressBar.printProgress(25); // 25% ready

                if (cacheData.isActualCache(LocalDate.now())) {
                    result = cacheData.getValue(console.getFrom(), console.getTo());
                    progressBar.printProgress(50); // 50% ready
                    if (result == null) {
                        String jsonResp =  getRequest(console.getFrom(), console.getTo());
                        ApiResponse apiResponse = parseJsonToApiResponse(jsonResp);
                        result = apiResponse.toString();
                        cacheData.addValue(result);
                        progressBar.printProgress(75); // 75% ready
                    }

                } else {
                    String jsonResp =  getRequest(console.getFrom(), console.getTo());
                    ApiResponse apiResponse = parseJsonToApiResponse(jsonResp);
                    result = apiResponse.toString();
                    cacheData.addValue(result);
                    progressBar.printProgress(75); // 75% ready

                }
                System.out.println("\r" + result);
                Thread threadSaveCache = new Thread() {
                    @Override
                    public void run() {
                        cacheData.saveToFile();

                    }
                };
                threadSaveCache.start();
                progressBar.printProgress(100); //100% done
            }

        }
    }

}

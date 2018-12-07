package joana.test.swapimaster.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){

            Gson gson = new GsonBuilder()
                    .setDateFormat("dd-MM-yyyy'T'HH:mm:ssZ")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.INSTANCE.getBASE_URL())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}

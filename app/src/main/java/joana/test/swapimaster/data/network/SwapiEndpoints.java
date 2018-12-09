package joana.test.swapimaster.data.network;

import joana.test.swapimaster.data.model.Film;
import joana.test.swapimaster.data.model.People;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SwapiEndpoints {

    @GET("people")
    Call<People> getPeople(
            @Query("page") int pageIndex
    );

    @GET("people")
    Call<People> searchInPeople(
            @Query("search") String data,
            @Query("page") int pageIndex
    );

    @GET("films/{id}/")
    Call<Film> getFilm(
            @Path("id") Integer id
    );
}

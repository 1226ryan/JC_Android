package restaurant.study.com.test_weather;

import android.support.annotation.Nullable;

public interface ResultCallback<T> {
    void onSuccess(@Nullable T t);
    void onFailure(Throwable t);
}

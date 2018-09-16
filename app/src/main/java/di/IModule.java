package di;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import bean.Bean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.HttpUtils;

/**
 * Created by YangYueXiang
 * on 2018/9/15
 */
public class IModule implements IContract.imodule{
    private String path="https://www.apiopen.top/meituApi?page=1";
    @Override
    public void requestData(final calllisten calllisten) {
        HttpUtils httpUtils = HttpUtils.getinstance();
        httpUtils.getData(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
             //   Log.d("xxx", string+"");

                Gson gson = new Gson();
                Bean bean = gson.fromJson(json, Bean.class);
                List<Bean.DataBean> data = bean.getData();
                calllisten.responmsg(data);
            }
        });
    }
}

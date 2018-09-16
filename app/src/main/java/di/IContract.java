package di;

import java.util.List;

import bean.Bean;

/**
 * Created by YangYueXiang
 * on 2018/9/15
 */
public interface IContract {
    /**
     * iview
     */
    interface iview{
        void showData(List<Bean.DataBean> data);
    }
    /**
     * ipresenter
     */
    interface ipresenter<iview>{
        void attachView(iview iview);
        void detachView(iview iview);
        void requestInfo();
    }
    /**
     * imodule
     */
    interface imodule{
        interface calllisten{
            void responmsg(List<Bean.DataBean> data);
        }
        void requestData(calllisten calllisten);
    }
}

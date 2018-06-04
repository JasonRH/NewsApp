package com.example.rh.newsapp.database;

import android.util.Log;

import com.example.rh.newsapp.model.Channel;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RH
 * @date 2018/6/2
 */
public class ChannelDao {

    public static List<Channel> findAll() {
        return DataSupport.findAll(Channel.class);
    }

    public static boolean save(List<Channel> selected, List<Channel> unSelected) {
        //删除表内所有数据
        DataSupport.deleteAll(Channel.class);

        try {
            /*for (Channel channel : selected){
                channel.setChannelSelect(true);
                Log.e("ChannelDao", ": 1111"+channel.getChannelId() );
                Log.e("ChannelDao", ": 1111"+channel.getChannelName() );
                channel.save();
            }
            for (Channel channel : unSelected){
                channel.setChannelSelect(false);
                channel.save();
            }*/
            for (int i = 0; i < selected.size(); i++) {
                Channel channel = new Channel();
                channel.setChannelId(selected.get(i).getChannelId());
                channel.setChannelName(selected.get(i).getChannelName());
                channel.setChannelSelect(true);
                channel.save();
            }

            for (int i = 0; i < unSelected.size(); i++) {
                Channel channel = new Channel();
                channel.setChannelId(unSelected.get(i).getChannelId());
                channel.setChannelName(unSelected.get(i).getChannelName());
                channel.setChannelSelect(false);
                channel.save();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        /*DataSupport.saveAllAsync(channels).listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
            }
        });*/

    }
}

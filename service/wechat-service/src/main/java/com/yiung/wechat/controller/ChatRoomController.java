package com.yiung.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.yiung.api.entity.ChatRoom;
import com.yiung.config.configcenter.params.ReqParams;
import com.yiung.config.configcenter.params.ResponseVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chatRoom")
public class ChatRoomController {

    /**
     * getChatRoomList
     * @author yiung
     * @param reqParams userProperty 0-1
     * @return ResponseVo
     **/
    @RequestMapping(value = "/getChatRoomList")
    public ResponseVo getChatRoomList(@RequestBody ReqParams reqParams) {
        JSONObject jsonObject = reqParams.getJSONObject();
        int userProperty = jsonObject.getInteger("userProperty");
        List<ChatRoom> chatRoomList = new ArrayList<>();
        //模拟返回一个房间
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomCode(1);
        chatRoom.setIcon("https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epfacxLtmw2VUmtmQxCnknXdbXQ6OXAvPLlOdU3TUneMKVX0PjFaxTUib3lRPiahj19Zvwzs6XY8Gqw/132");
        chatRoom.setTitle("Help Room");
        chatRoom.setDesc("Hanna");
        chatRoom.setNavigateTo("../videocall/videocall?roomID=1");

        if(userProperty ==1){
            //获取自己房间
            chatRoomList.add(chatRoom);
        }else if(userProperty ==0){
            //获取一个服务者房间，这里以后会算法分配
            chatRoomList.add(chatRoom);
        }
        return new ResponseVo(chatRoomList);
    }
}

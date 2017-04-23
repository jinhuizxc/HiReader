package com.example.jh.hireader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jh.hireader.R;
import com.example.jh.hireader.chat.ChatContract;
import com.example.jh.hireader.ui.adapter.ChatAdapter;
import com.example.jh.hireader.chat.bean.ChatMsgBean;
import com.example.jh.hireader.presenter.ChatPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinhui  on 2017/4/20
 * 邮箱: 1004260403@qq.com
 */

public class ChatFragment extends Fragment implements ChatContract.View {

    private static final String TAG = "ChatFragment";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EditText editText;
    private Button button;
    private ChatContract.Presenter presenter;
    private ChatAdapter chatAdapter;
    private List<ChatMsgBean> list = new ArrayList<>();

    public static ChatFragment newIntance() {
        return new ChatFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ChatPresenter(getContext(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        initView(view);

        ChatMsgBean chatMsgBean = new ChatMsgBean();
        chatMsgBean.setType(ChatMsgBean.TYPE_RECIV);
        chatMsgBean.setMsg("你好,我是智能机器人");
        list.add(chatMsgBean);
        chatAdapter = new ChatAdapter(getContext(), list);
        recyclerView.setAdapter(chatAdapter);
        return view;
    }

    @Override
    public void setPresenter(ChatContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.robot_rl);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        //recyclerView.smoothScrollOffset(offset);
        //  linearLayoutManager.setStackFromEnd(true);
        // linearLayoutManager.scrollToPositionWithOffset(2, 20);
        recyclerView.setLayoutManager(linearLayoutManager);
        editText = (EditText) view.findViewById(R.id.robot_edit);
        button = (Button) view.findViewById(R.id.robot_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendMsg(editText.getText().toString().trim());
                ChatMsgBean chatMsgBean = new ChatMsgBean();
                chatMsgBean.setType(ChatMsgBean.TYPE_SEND);
                chatMsgBean.setMsg(editText.getText().toString());
                list.add(chatMsgBean);
                chatAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(list.size() - 1);

                editText.setText("");

            }
        });
    }

    @Override
    public void showError() {

    }

    @Override
    public void showReslut(ChatMsgBean data) {
        list.add(data);
        chatAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(list.size() - 1);
    }
}

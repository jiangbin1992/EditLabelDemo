package com.jiangbin.editlabeldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.library.AutoFlowLayout;

import java.util.ArrayList;
import java.util.List;


import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;

public class MainActivity extends AppCompatActivity {
    private List<String> netWorkList = new ArrayList<String>();//上面的标签列表
    private AutoFlowLayout mFlowLayout;
    private AutoFlowLayout mFlowLayoutTui;
    private LayoutInflater mLayoutInflater;
    private EditText tvAttrTagEdittext;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initData();
    }


    private void initView() {
        mLayoutInflater = LayoutInflater.from(this);
        mFlowLayout = findViewById(R.id.flowLayout);
        mFlowLayoutTui = findViewById(R.id.mFlowLayout);
        TextView tv = findViewById(R.id.tv);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mFlowLayout.getChildCount() - 1; i++) {
                    TextView textView = mFlowLayout.getChildAt(i).findViewById(R.id.labelTv);
                    Logger.d("gggdeleteddddd" + textView.getText());
                }
            }
        });
    }

    private void initData() {
        try {
            View item = mLayoutInflater.inflate(R.layout.item_label1, null);
            tvAttrTagEdittext = item.findViewById(R.id.labelTvs);
            mFlowLayout.addView(item);
            tvAttrTagEdittext.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {

                    if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent
                            .ACTION_DOWN) {// 修改回车键功能
                        // 先隐藏键盘
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(
                                        getCurrentFocus().getWindowToken(), InputMethodManager
                                                .HIDE_NOT_ALWAYS);

                        if (!TextUtils.isEmpty(tvAttrTagEdittext.getText().toString())) {

                            View item = mLayoutInflater.inflate(R.layout.item_label, null);
                            TextView tvAttrTag = item.findViewById(R.id.labelTv);
                            tvAttrTag.setText(tvAttrTagEdittext.getText().toString());
                            tvAttrTagEdittext.setText("");
                            tvAttrTagEdittext.setHint("添加标签");
                            tvAttrTagEdittext.clearFocus();
                            mFlowLayout.addView(item, mFlowLayout.getChildCount() - 1);


                        }
                    }
                    return false;
                }
            });

            mFlowLayout.setOnLongItemClickListener(new AutoFlowLayout.OnLongItemClickListener() {
                @Override
                public void onLongItemClick(final int i, final View view) {
                    QuickPopupBuilder.with(mContext)
                            .contentView(R.layout.popup_normal)
                            .config(new QuickPopupConfig()
                                    .gravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM)
                                    .withClick(R.id.down_delete_tv, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // try {
                                            TextView textView = view.findViewById(R.id.labelTv);
                                            setNetWorkStatues(textView.getText().toString());
                                            //mFlowLayout.deleteView(i);
                                            mFlowLayout.removeView(view);

                                            Logger.d("gggdeletelist" + mFlowLayout.getChildCount());

//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
                                        }
                                    }, true).withClick(R.id.down_cancel_tv, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                        }
                                    }, true))
                            .show(view);
                }
            });

            netWorkList.add("语文");
            netWorkList.add("语文1");
            netWorkList.add("语文2");
            netWorkList.add("语文3");
            netWorkList.add("语文4");
            netWorkList.add("语文5");
            netWorkList.add("语文6");
            mFlowLayoutTui.setMultiChecked(true);
            for (int i = 0; i < netWorkList.size(); i++) {
                View item1 = mLayoutInflater.inflate(R.layout.item_label2, null);
                TextView tvAttrTag = item1.findViewById(R.id.labelTv);
                tvAttrTag.setText(netWorkList.get(i));
                mFlowLayoutTui.addView(item1);

            }
            mFlowLayoutTui.setOnItemClickListener(new AutoFlowLayout.OnItemClickListener() {
                @Override
                public void onItemClick(int i, View view) {
                    // try {
                    if (mFlowLayout.getChildCount() == 1) {
                        View item = mLayoutInflater.inflate(R.layout.item_label, null);
                        TextView tvAttrTag = item.findViewById(R.id.labelTv);
                        tvAttrTag.setText(netWorkList.get(i));
                        mFlowLayout.addView(item, mFlowLayout.getChildCount() - 1);
                        view.setSelected(true);
                        return;
                    } else {
                        if (checkmFlowLayout(netWorkList.get(i))) {
                            View item = mLayoutInflater.inflate(R.layout.item_label, null);
                            TextView tvAttrTag = item.findViewById(R.id.labelTv);
                            tvAttrTag.setText(netWorkList.get(i));
                            mFlowLayout.addView(item, mFlowLayout.getChildCount() - 1);
                            view.setSelected(true);
                        } else {
                            view.setSelected(false);
                        }

                    }


//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkmFlowLayout(String value) {
        boolean result = false;
        for (int a = 0; a < mFlowLayout.getChildCount() - 1; a++) {
            TextView textView = mFlowLayout.getChildAt(a).findViewById(R.id.labelTv);
            if (value.equals(textView.getText().toString())) {
                mFlowLayout.deleteView(a);
                result = false;
                break;
            } else {
                result = true;
            }

        }
        return result;
    }

    private void setNetWorkStatues(String value) {
        // try {
        if (!TextUtils.isEmpty(value)) {
            for (int i = 0; i < netWorkList.size(); i++) {
                if (value.equals(netWorkList.get(i))) {
                    mFlowLayoutTui.getChildAt(i).setSelected(false);
                }
            }
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}

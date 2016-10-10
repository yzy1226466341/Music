package com.yzy.music.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * RecycleViewAdapter基类(可直接初始化)
 * Created by yzy on 2016/9/26.
 */
public class BaseRecycleViewAdapter<T,K extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<K>> {

    protected List<T> lists;    //数据源
    protected int resouceId;    //布局ID
    protected int variableId;   //布局内VariableId

    public BaseRecycleViewAdapter(List<T> lists, int resouceId,int variableId) {
        this.lists = lists;
        this.resouceId = resouceId;
        this.variableId = variableId;
    }

    /**
     * 创建绑定数据的ViewHolder(实际上就相当于初始化出来界面)
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseViewHolder<K> onCreateViewHolder(ViewGroup parent, int viewType){
        K itemBing = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),resouceId,parent,false);   //获取DataBing相当于获取View
        BaseViewHolder<K> holder = new BaseViewHolder<K>(itemBing.getRoot());//初始化ViewHolder存放View
        holder.setDataBing(itemBing);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder<K> holder, final int position){
        T data = lists.get(position);//获取数据
        holder.getDataBing().setVariable(variableId,data);//赋值
        if(listener!=null) {//设置单击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(holder.getDataBing(),position);
                }
            });
        }
        holder.getDataBing().executePendingBindings();//刷新界面
    }

    @Override
    public int getItemCount() {
        return lists == null?0:lists.size();
    }

    /**
     * TODO: 对RecyclerView的数据更新,必须remove掉Item以及DataItem,这样才会触发item改变的动画,如果直接调用notifyDataSetChanged则不会触发动画的效果
     */
    /**
     * 插入Item，并且更新界面
     * @param t
     * @param position
     */
    public void addItem(T t , int position){
        if(lists == null){
            lists = new ArrayList<>();
        }
        if(position == -1){
            position = lists.size();
        }
        lists.add(position,t);
        notifyItemInserted(position);
    }

    public void addItem(T t){
        addItem(t,-1);
    }

    /**
     * 添加Items
     * @param ts
     * @param position
     */
    public void addItems(List<T> ts,int position){
        if(lists == null){
            lists = new ArrayList<>();
        }
        if(position == -1){
            position = lists.size();
        }
        lists.addAll(position,ts);
        notifyItemInserted(position);
    }

    public void addItems(List<T> ts){
        addItems(ts,-1);
    }

    /**
     * remove Item
     * @param t
     */
    public void removeItem(T t){
        if(lists == null){
            notifyDataSetChanged();
            return ;
        }
        int position = lists.indexOf(t);
        if(position != -1) {
            lists.remove(t);
            notifyItemRemoved(position);
        }
    }

    public void removeItem(int position){
        if(lists == null){
            notifyDataSetChanged();
            return ;
        }
        if(position>=0 && position<lists.size()) {
            lists.remove(position);
            notifyItemRemoved(position);
        }
    }



    //自定义item单击事件
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        public void onItemClick(ViewDataBinding dataBinding, int position);
    }

}

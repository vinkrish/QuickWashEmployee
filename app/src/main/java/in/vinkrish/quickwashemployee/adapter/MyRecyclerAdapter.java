package in.vinkrish.quickwashemployee.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import in.vinkrish.quickwashemployee.Order;
import in.vinkrish.quickwashemployee.R;

/**
 * Created by vinkrish on 17/12/15.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {
    private Context context;
    private List<Order> orderList;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_row, viewGroup, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Order orderItem = orderList.get(i);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");

        customViewHolder.dateTV.setText(orderItem.getDate());
        customViewHolder.serviceTV.setText(orderItem.getService());
        customViewHolder.nameTV.setText(orderItem.getName());
        customViewHolder.mobileTV.setText(orderItem.getMobile());
        customViewHolder.addressTV.setText(orderItem.getAddress());
        customViewHolder.pincodeTV.setText(orderItem.getPincode());
        customViewHolder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + orderItem.getMobile()));
                context.startActivity(intent);
            }
        });

        customViewHolder.dateTV.setTypeface(tf);
        customViewHolder.nameTV.setTypeface(tf);
        customViewHolder.mobileTV.setTypeface(tf);
        customViewHolder.addressTV.setTypeface(tf);
        customViewHolder.pincodeTV.setTypeface(tf);
    }

    @Override
    public int getItemCount() {
        return (null != orderList ? orderList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTV;
        private TextView serviceTV;
        private TextView nameTV;
        private TextView mobileTV;
        private TextView addressTV;
        private TextView pincodeTV;
        private ImageButton callBtn;

        CustomViewHolder(View view) {
            super(view);

            this.dateTV = view.findViewById(R.id.date_tv);
            this.serviceTV = view.findViewById(R.id.service_tv);
            this.nameTV = view.findViewById(R.id.name_tv);
            this.mobileTV = view.findViewById(R.id.mobile_tv);
            this.addressTV = view.findViewById(R.id.address_tv);
            this.pincodeTV = view.findViewById(R.id.pincode_tv);
            this.callBtn = view.findViewById(R.id.call_btn);
        }

    }
}

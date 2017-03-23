package com.example.pat.aapkatrade.parallax_recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.MainActivity;
import com.example.pat.aapkatrade.R;
import com.poliveira.parallaxrecycleradapter.HeaderLayoutManagerFixed;
import com.poliveira.parallaxrecycleradapter.ParallaxRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ParallaxActivity extends AppCompatActivity
{

    RecyclerView mRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parallax);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        createCardAdapter(mRecyclerView);

    }



    private void createCardAdapter(RecyclerView recyclerView) {
        final List<String> content = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            content.add("item " + i);
        }
        final ParallaxRecyclerAdapter<String> adapter = new ParallaxRecyclerAdapter<>(content);
        HeaderLayoutManagerFixed layoutManagerFixed = new HeaderLayoutManagerFixed(this);
        recyclerView.setLayoutManager(layoutManagerFixed);
        View header = getLayoutInflater().inflate(R.layout.simple_header, recyclerView, false);
        layoutManagerFixed.setHeaderIncrementFixer(header);
        adapter.setShouldClipView(true);
        adapter.setParallaxHeader(header, recyclerView);
        adapter.setData(content);
        adapter.implementRecyclerAdapterMethods(new ParallaxRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                ((ViewHolder) viewHolder).textView.setText(adapter.getData().get(i));
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                final ViewHolder holder = new ViewHolder(getLayoutInflater().inflate(R.layout.row_recyclerview_cards, viewGroup, false));
                //don't set listeners on onBindViewHolder. For more info check http://androidshenanigans.blogspot.pt/2015/02/viewholder-pattern-common-mistakes.html
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ParallaxActivity.this, "You clicked '" + adapter.getData().get(holder.getPosition() - (adapter.hasHeader() ? 1 : 0)) + "'", Toast.LENGTH_SHORT).show();
                    }
                });
                return holder;
            }

            @Override
            public int getItemCount() {
                return content.size();
            }
        });
        recyclerView.setAdapter(adapter);
    }




    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}


package com.rockspin.androiddevtest.mian;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.rockspin.androiddevtest.MyApplication;
import com.rockspin.androiddevtest.R;
import com.rockspin.androiddevtest.api.model.CosmonautActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String DATA_STATE_KEY = "stateKey";
    private static final String DATA_CONTENT = "content";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private CosmonautActivityAdapter cosmonautActivitiesAdapter;
    private boolean isSortedAscending = true;
    private List<CosmonautActivity> cosmonautActivities;
    private LinearLayoutManager linearLayoutManager;
    private Parcelable dataState;

    @OnClick(R.id.fab_change_sorting)
    public void sortData() {
        Collections.sort(cosmonautActivities, (firstCosmonautActivity, secondCosmonautActivity) -> {
            try {
                Date firstDate = simpleDateFormat.parse(firstCosmonautActivity.getDate());
                Date secondDate = simpleDateFormat.parse(secondCosmonautActivity.getDate());
                if (isSortedAscending) {
                    if (firstDate.after(secondDate))
                        return -1;
                    else
                        return 1;

                } else {
                    if (firstDate.before(secondDate))
                        return -1;
                    else
                        return 1;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });
        isSortedAscending = !isSortedAscending;
        cosmonautActivitiesAdapter.setCosmonautActivityList(cosmonautActivities);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupRecyclerView();
        if (savedInstanceState == null) {
            MyApplication.getAPIService().getEVList().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dataState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(dataState);
            cosmonautActivitiesAdapter.setCosmonautActivityList(cosmonautActivities);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        dataState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(DATA_STATE_KEY, dataState);
        outState.putParcelableArrayList(DATA_CONTENT,
                (ArrayList<? extends Parcelable>) cosmonautActivities);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            cosmonautActivities = savedInstanceState.getParcelableArrayList(DATA_CONTENT);
            dataState = savedInstanceState.getParcelable(DATA_STATE_KEY);
        }
    }

    private void setupRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                linearLayoutManager.getOrientation()));

        cosmonautActivitiesAdapter = new CosmonautActivityAdapter(this);
        recyclerView.setAdapter(cosmonautActivitiesAdapter);
    }

    private void handleError(Throwable throwable) {
        Toast.makeText(this, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
    }

    private void handleResponse(List<CosmonautActivity> cosmonautActivitie) {
        this.cosmonautActivities = cosmonautActivitie;
        cosmonautActivitiesAdapter.setCosmonautActivityList(cosmonautActivitie);
    }
}

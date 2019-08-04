package com.example.kaodimlistservices.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kaodimlistservices.R;
import com.example.kaodimlistservices.adapter.SectionAdapter;
import com.example.kaodimlistservices.model.ServiceSubgroup;
import com.example.kaodimlistservices.model.ServiceType;
import com.example.kaodimlistservices.remote.GetDataService;
import com.example.kaodimlistservices.remote.RetrofitClientInstance;
import com.example.kaodimlistservices.remote.Section;
import com.shuhart.stickyheader.StickyHeaderItemDecorator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private View v;
    private List<ServiceSubgroup> headerList = new ArrayList<>();
    private List<ServiceType> itemList = new ArrayList<>();
    private ArrayList<Section> sectionArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        callService();

        return v;
    }

    private void callService() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<ServiceSubgroup>> call = service.getHomeService();
        call.enqueue(new Callback<List<ServiceSubgroup>>() {
            @Override
            public void onResponse(Call<List<ServiceSubgroup>> call, Response<List<ServiceSubgroup>> response) {
                headerList = response.body();
                int size = headerList.size();

                for (int i = 0; i < size; i++) {
                    ServiceSubgroup serviceSubgroup = new ServiceSubgroup(i);
                    serviceSubgroup.setName(headerList.get(i).getName());
                    sectionArrayList.add(serviceSubgroup);

                    itemList = headerList.get(i).getServiceTypes();
                    int size2 = itemList.size();
                    for (int t = 0; t < size2; t++) {
                        ServiceType serviceType = new ServiceType(t);
                        serviceType.setName(itemList.get(t).getName());
                        serviceType.setImageThumbUrl(itemList.get(t).getImageThumbUrl());
                        sectionArrayList.add(serviceType);
                    }
                }
                setLayout();
            }

            @Override
            public void onFailure(Call<List<ServiceSubgroup>> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLayout() {
        RecyclerView recyclerView = v.findViewById(R.id.rv_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerDecorator);

        SectionAdapter adapter = new SectionAdapter(getActivity(),sectionArrayList);
        recyclerView.setAdapter(adapter);

        /*StickyHeaderItemDecorator decorator = new StickyHeaderItemDecorator(adapter);
        decorator.attachToRecyclerView(recyclerView);*/
    }
}

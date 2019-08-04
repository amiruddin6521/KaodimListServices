# KaodimListServices
Kaodim Android Internship Assignment

## Challanges
- Getting list of services API using Retrofit where i need to retrive an Object arrays in Object arrays.
- Hard to handle data from API into recycleview where there need a header and content inside recycleview.

## Core Parts of Codes
Core parts of coding to make this apps work is as below :-

SectionAdapter.java
```
public class SectionAdapter extends StickyAdapter<RecyclerView.ViewHolder, RecyclerView.ViewHolder> {
    private ArrayList<Section> sectionArrayList;
    private static final int LAYOUT_HEADER= 0;
    private static final int LAYOUT_CHILD= 1;
    private Context context;

    public SectionAdapter(Context context, ArrayList<Section> sectionArrayList){

        this.context = context;
        this.sectionArrayList = sectionArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == LAYOUT_HEADER ) {
            return new HeaderViewholder(inflater.inflate(R.layout.header_layout, parent, false));
        }else {
            return new ItemViewHolder(inflater.inflate(R.layout.item_layout, parent, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (sectionArrayList.get(position).isHeader()) {
            ((HeaderViewholder) holder).textViewH.setText(sectionArrayList.get(position).getName());
        } else {
            ((ItemViewHolder) holder).textViewI.setText(sectionArrayList.get(position).getName());

            if(sectionArrayList.get(position).getImageThumbUrl() == null) {
                Picasso.Builder builder = new Picasso.Builder(context);
                builder.downloader(new OkHttp3Downloader(context));
                builder.build().load("null")
                        .placeholder((R.drawable.placeholder))
                        .error(R.drawable.error)
                        .into(((ItemViewHolder) holder).roundedImageViewI);
            } else {
                Picasso.Builder builder = new Picasso.Builder(context);
                builder.downloader(new OkHttp3Downloader(context));
                builder.build().load(sectionArrayList.get(position).getImageThumbUrl().getLg())
                        .placeholder((R.drawable.placeholder))
                        .error(R.drawable.error)
                        .into(((ItemViewHolder) holder).roundedImageViewI);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(sectionArrayList.get(position).isHeader()) {
            return LAYOUT_HEADER;
        }else
            return LAYOUT_CHILD;
    }

    @Override
    public int getItemCount() {
        return sectionArrayList.size();
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        //Log.d("seeee",""+itemPosition+"......"+sectionArrayList.get(itemPosition).sectionPosition());
        return sectionArrayList.get(itemPosition).sectionPosition();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int headerPosition) {
        ((HeaderViewholder) holder).textViewH.setText( sectionArrayList.get(headerPosition).getName());
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return createViewHolder(parent, LAYOUT_HEADER);
    }

    public static class HeaderViewholder extends RecyclerView.ViewHolder {
        TextView textViewH;

        HeaderViewholder(View itemView) {
            super(itemView);
            textViewH = itemView.findViewById(R.id.servHeader);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewI;
        RoundedImageView roundedImageViewI;

        ItemViewHolder(View itemView) {
            super(itemView);
            textViewI = itemView.findViewById(R.id.servName);
            roundedImageViewI = itemView.findViewById(R.id.servImg);
        }
    }
}
```
This is the important methods of Adapter where :-
- In the constructor of this adapter, i'm getting an arraylist with objects of the interface "Section".
- This arraylist contains the objects of both HeaderModel and ItemModel.
- With the arraylist i'm getting, it will set the header and content inside recycleview.



HomeFragment.java
```
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
```
In this area :-
- callService() function is used to call the API using Retrofit to retrieve the json data.
- From there, all data will convert into 1 arraylist that need to be use in SectionAdapter.java.
- In setLayout() function, it will call SectionAdapter to set the recyleview adapter.

## Test application (APK)
To test my app, you can use link below to download the apk file :-

[KaodimListServices.apk](https://github.com/amiruddin6521/KaodimListServices/raw/master/KaodimListServices.apk)

## Screenshots
<img src="https://github.com/amiruddin6521/KaodimListServices/blob/master/Screenshot/g1.gif" width="30%" height="30%"> <img src="https://github.com/amiruddin6521/KaodimListServices/blob/master/Screenshot/g2.gif" width="30%" height="30%">

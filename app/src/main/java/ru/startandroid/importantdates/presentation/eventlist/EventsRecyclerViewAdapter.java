package ru.startandroid.importantdates.presentation.eventlist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.startandroid.importantdates.R;
import ru.startandroid.importantdates.core.domain.Event;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Event}.
 */
public class EventsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Event> eventList;
    private final Activity activity;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private final int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param eventList List<Event> containing the data to populate views to be used
     *                  by RecyclerView.
     */
    public EventsRecyclerViewAdapter(RecyclerView recyclerView,
                                     List<Event> eventList,
                                     Activity activity) {
        this.eventList = eventList;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager =
                (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        isLoading = true;
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return eventList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        private final TextView dayTextView;
        private final TextView nameTextView;
        private final TextView categoryTextView;
        private final ImageView eventImageView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            this.dayTextView = itemView.findViewById(R.id.day_text_view);
            this.nameTextView = itemView.findViewById(R.id.name_text_view);
            this.categoryTextView = itemView.findViewById(R.id.category_text_view);
            this.eventImageView = itemView.findViewById(R.id.event_image_view);
        }

        public TextView getDayTextView() {
            return dayTextView;
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public TextView getCategoryTextView() {
            return categoryTextView;
        }

        public ImageView getEventImageView() {
            return eventImageView;
        }
    }

    // "Loading item" ViewHolder
    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.loading_spinner);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.event_item, parent, false);
            return new EventViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EventViewHolder) {
            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            Event eventItem = this.eventList.get(position);
            EventViewHolder eventViewHolder = (EventViewHolder) holder;
            Activity mainActivity = this.activity;

            eventViewHolder.itemView.setOnClickListener(view -> {
                //TODO: open event editor/creator
                //Intent intent = new Intent(mainActivity, )
            });

            eventViewHolder.getDayTextView().setText(String.valueOf(eventItem.getDay()));
            eventViewHolder.getNameTextView().setText(eventItem.getName());
            eventViewHolder.getCategoryTextView().setText(eventItem.getCategory().getName());
            eventViewHolder.getEventImageView().setImageBitmap(eventItem.getBitmapImage());

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    @Override
    public int getItemCount() {
        return this.eventList == null ? 0 : this.eventList.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    /**
     * Clear all items in the adapter
     */
    public void clear() {
        if (this.eventList == null) return;
        int size = this.eventList.size();
        this.eventList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void update(List<Event> newEvents) {
        clear();
        this.eventList.addAll(newEvents);
        notifyItemRangeInserted(0, newEvents.size());
    }
}
package ajman.university.grad.project.eventshare.adapters;

import java.util.ArrayList;
import java.util.List;

import ajman.university.grad.project.eventshare.admin.R;
import ajman.university.grad.project.eventshare.common.contracts.IErrorService;
import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.models.Event;
import ajman.university.grad.project.eventshare.common.services.ServicesFactory;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventsAdapter extends BaseAdapter {
	private List<Event> events = new ArrayList<Event>();
	private Context context;

	public EventsAdapter(Context c) {
		context = c;
		events = new ArrayList<Event>();
		 
		// Get the events from the events repository
		ILocalStorageService service = ServicesFactory.getLocalStorageService();
		try {
			List<Event> storedEvents = service.getAllEvents();
			for(Event event : storedEvents) {
				events.add(event);
			}
		} catch (Exception e) {
			// TODO: Error service
			IErrorService eService = ServicesFactory.getErrorService();
			eService.log(e);
		}
	}
	 
	@Override
	public int getCount() {
		return events.size();
	}

	@Override
	public Object getItem(int i) {
		return events.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.single_row_list, viewGroup,false); // contains a reference to the Relative layout
		
		TextView title = (TextView) row.findViewById(R.id.textView1);
		TextView location = (TextView) row.findViewById(R.id.textView2);
		TextView description = (TextView) row.findViewById(R.id.textView3);
		
		
		Event event = events.get(i);
		
		title.setText(event.getTitle());
		description.setText(event.getDescription());
		location.setText(event.getLocation());

		return row; //return the rootView of the single_row_list.xml
	}
}

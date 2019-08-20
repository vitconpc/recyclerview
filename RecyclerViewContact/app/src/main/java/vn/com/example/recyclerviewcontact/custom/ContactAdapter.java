package vn.com.example.recyclerviewcontact.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import vn.com.example.recyclerviewcontact.R;
import vn.com.example.recyclerviewcontact.data.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements ItemTouchHelperAdapter {

    private Context context;
    private List<Contact> contacts;
    private ContactEvent event;

    public ContactAdapter(Context context, List<Contact> contacts, ContactEvent event) {
        this.context = context;
        this.contacts = contacts;
        this.event = event;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ContactViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_item_contact, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {
        contactViewHolder.setData(contacts.get(i));
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView txtContactName;
        private TextView txtContactNumber;
        private LinearLayout layoutRoot;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            setEvent();
        }

        private void setEvent() {
            layoutRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    event.eventClick(getAdapterPosition());
                }
            });
        }

        private void initView(View itemView) {
            layoutRoot = itemView.findViewById(R.id.item_view);
            txtContactName = itemView.findViewById(R.id.txt_name);
            txtContactNumber = itemView.findViewById(R.id.txt_number);
        }

        public void setData(Contact contact) {
            txtContactNumber.setText(contact.getContactNumber());
            txtContactName.setText(contact.getContactName());
        }
    }

    @Override
    public void onItemDismiss(int position) {
        contacts.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(contacts, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(contacts, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }
}

package com.example.sae;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class HelpAdapter extends BaseExpandableListAdapter
{
    private Context context;
    private List<String> listHeaders; // Liste des questions
    private HashMap<String, List<String[]>> listChildren; // Liste des sous questions/réponses

    public HelpAdapter(Context context, List<String> listHeaders, HashMap<String, List<String[]>> listChildren) {
        this.context = context;
        this.listHeaders = listHeaders;
        this.listChildren = listChildren;
    }

    @Override
    public int getGroupCount() {
        return listHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChildren.get(listHeaders.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listHeaders.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChildren.get(listHeaders.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_item_aide, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle);

        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String[] childData = (String[]) getChild(groupPosition, childPosition);
        String subQuestion = childData[0]; // 🔥 Sous-question
        String answer = childData[1]; // 🔥 Réponse

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_element_liste_faq, null);
        }

        TextView lblSubQuestion = convertView.findViewById(R.id.lblSubQuestion);
        TextView lblAnswer = convertView.findViewById(R.id.lblAnswer);

        lblSubQuestion.setText(subQuestion);
        lblAnswer.setText(answer);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

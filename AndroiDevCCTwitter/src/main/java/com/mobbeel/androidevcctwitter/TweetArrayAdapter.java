package com.mobbeel.androidevcctwitter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.mobbeel.androidevcctwitter.model.Tweet;
import com.mobbeel.androidevcctwitter.model.TwitterUser;
import com.mobbeel.androidevcctwitter.volley.VolleyManager;

/**
 * Adapter to bridge between the ListView and the classes that load the tweets from the network.
 *
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {


    public TweetArrayAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_list_item, null);
            holder = new ViewHolder();
            holder.imageView = (NetworkImageView) convertView.findViewById(R.id.twitterUserImage);
            holder.usernameView = (TextView) convertView.findViewById(R.id.username);
            holder.textView = (TextView) convertView.findViewById(R.id.message);
            holder.dateView = (TextView) convertView.findViewById(R.id.tweetTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Tweet currentTweet = getItem(position);

        holder.textView.setText(currentTweet.getText());
        holder.dateView.setText(currentTweet.getFormatedTime());

        TwitterUser user = currentTweet.getUser();
        if (user != null) {
            holder.usernameView.setText(user.getName() + " (@" + currentTweet.getUser().getUsername() + ")");
            holder.imageView.setImageUrl(user.getProfileImageUrl(), VolleyManager.getImageLoader());
        } else {
            holder.usernameView.setText("");
            //TODO: clear image?
        }




        return convertView;
    }

    private static class ViewHolder {
        private NetworkImageView imageView;
        private TextView usernameView;
        private TextView textView;
        private TextView dateView;
    }
}

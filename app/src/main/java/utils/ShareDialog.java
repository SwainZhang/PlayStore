package utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.emery.test.playstore.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ShareDialog extends Dialog {
	final int[] DEFAULT_IMAGES = new int[] { R.drawable.share_wechat,
			R.drawable.share_pengyou, R.drawable.share_qq,
			R.drawable.share_qzone, R.drawable.share_weibo };
	final int[] DEFAULT_IDS = new int[] { R.string.share_wechat,
			R.string.share_moments, R.string.share_qq, R.string.share_qzone,
			R.string.share_weibo };

	private Context context;
	private int[] images = DEFAULT_IMAGES;
	private int[] ids = DEFAULT_IDS;

	private View.OnClickListener itemListener;

	public ShareDialog(Context context) {
		super(context);
		this.context = context;
	}

	public ShareDialog(Context context, int[] images, int[] ids) {
		super(context);
		this.context = context;
		this.images = images;
		this.ids = ids;
	}

	public int[] getImages() {
		return images;
	}

	public void setImages(int[] images) {
		this.images = images;
	}

	public int[] getIds() {
		return ids;
	}

	public void setIds(int[] ids) {
		this.ids = ids;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merge_share);
		Window window = getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.shareDialogStyle);
		window.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
		initShareLayout();
	}

	private void initShareLayout() {

		GridView gridView = (GridView) this.findViewById(R.id.layout_share_icons);

		// curSkuInfo = skus.get(0);
		List<Map<String, String>> items = new ArrayList<Map<String, String>>();

		for (int i = 0; i < images.length; i++) {
			int id = images[i];
			int name = ids[i];
			Map<String, String> item = new HashMap<String, String>();
			item.put("image", String.valueOf(id));
			item.put("text", context.getString(name));
			items.add(item);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(context, items, R.layout.share_grid_view_item,
				new String[] { "image", "text" }, new int[] { R.id.share_image, R.id.share_text });
		gridView.setAdapter(simpleAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (null != itemListener) {
					view.setId(images[position]);
					itemListener.onClick(view);
				}
			}
		});

		View cancel = this.findViewById(R.id.btn_share_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public final void setItemListener(
			View.OnClickListener itemListener) {
		this.itemListener = itemListener;
	}

}

package base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.emery.test.playstore.MainActivity;

import java.util.LinkedList;
import java.util.List;


/**
 * @author  Administrator
 * @time 	2016-7-19 下午4:14:18
 * @des	TODO
 *
 * @version $Rev: 45 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2016-07-19 17:01:18 
 * @updateDes TODO
 */
public abstract class BaseActivity extends AppCompatActivity {
	// 共同属性
	// 共同的方法
	private List<ActionBarActivity> activities	= new LinkedList<ActionBarActivity>();
	private long					mPreTime;
	private Activity mCurActivity;

	/**
	 * 得到最上层activity
	 * @return
	 */
	public Activity getCurActivity() {
		return mCurActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		initView();
		initActionBar();
		initData();
		initListener();
		activities.add((ActionBarActivity) mCurActivity);
	}

	@Override
	protected void onDestroy() {
		activities.remove(this);
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		mCurActivity = this;// 最上层的一个activity
		super.onResume();
	}

	public void init() {
		// TODO

	}

	public abstract void initView();

	public void initActionBar() {
		// TODO

	}

	public void initData() {
		// TODO

	}

	public void initListener() {
		// TODO

	}

	/**
	 * 完全退出
	 */
	public void exit() {
		for (ActionBarActivity activity : activities) {
			activity.finish();
		}
	}

	/**
	 * 统一退出控制
	 */
	@Override
	public void onBackPressed() {
		if (mCurActivity instanceof MainActivity) {
			if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2s
				Toast.makeText(getApplicationContext(), "再按一次,退出谷歌市场", Toast.LENGTH_SHORT).show();
				mPreTime = System.currentTimeMillis();
				return;
			}
		}
		super.onBackPressed();// finish();
	}

}

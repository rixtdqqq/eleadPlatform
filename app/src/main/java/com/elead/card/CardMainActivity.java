/*
package com.elead.card;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.elead.card.cardviews.TbSelectCard;
import com.elead.card.mode.CardInfo;
import com.elead.card.mode.TbSelectCardInfo;
import com.elead.card.views.BaseWaveView;
import com.elead.views.pulltorefresh.EmptyView;
import com.elead.eplatform.R;

import java.util.ArrayList;
import java.util.List;

public class CardMainActivity extends Activity {

	private static final int REQUESTCODE_PICK = 1;
	private static final int REQUESTCODE_CUTTING = 2;
	private static final int SELECT_PIC_KITKAT = 3;
	private static final int SELECT_PIC = 4;
	protected static final String REQUEST_GET_DATA_CODE = "GET_DATA_CODE";
	protected static final String REQUEST_GET_MORE_DATA_CODE = "GET_MORE_DATA_CODE";
	protected String REQUEST_MARK_READ_CODE = "MARK_READ_CODE";
	private PullToRefreshListView lvMsgNotificationsEntryList;
	protected boolean isRefreshing;
	protected int currentPage;
	protected boolean isHasMore;
	private CardListAdapter adapter;
	private List<CardInfo> list = new ArrayList<CardInfo>();
	private CustomProgressDialog dialog;
	private BaseWaveView tu;
	private MyBrocastReciver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(new Visits(MainActivity.this));
		// setContentView(new Project(MainActivity.this));
		// setContentView(new FlingText(this));
		receiver = new MyBrocastReciver();
		IntentFilter filter = new IntentFilter(TbSelectCard.action);
		registerReceiver(receiver, filter);
		setContentView(R.layout.activity_card_main);
		initList();
		initDate();
	}

	private void initDate() {
		handler.sendEmptyMessageDelayed(100, 1);
	}

	class MyBrocastReciver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("action: " + intent.getAction());
			if (intent.getAction().contains(TbSelectCard.action)) {
				String content = (String) intent.getExtras().get(
						TbSelectCard.content);
				int index = (Integer) intent.getExtras()
						.get(TbSelectCard.INDEX);
				CardInfo cardInfo = new CardInfo("list_item_select_tb");
				for (int i = 0; i < list.size(); i++) {
					if (!list.get(i).equals(cardInfo)) {
						list.remove(i);
					}
				}

				switch (index) {
					case 0:
						list.add(new CardInfo("list_item_visits"));
						list.add(new CardInfo("list_empty"));
						break;
					case 1:
						list.add(new CardInfo("list_item_project"));
						list.add(new CardInfo("list_item_distribution"));
						break;

					default:
						break;
				}
				adapter.notifyDataSetChanged();
				System.out.println("content: " + content + "--INDEX:" + index);
			}

		}

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

				case 100:
					CardInfo cardInfo = new CardInfo("list_item_select_tb");
					List<String> items = new ArrayList<String>();
					items.add("产品人力");
					items.add("考勤状况");
					cardInfo.setDates(JSON.toJSONString(new TbSelectCardInfo(items)));
					list.add(cardInfo);
					list.add(new CardInfo("list_item_stable"));
					list.add(new CardInfo("list_item_simple_arc"));
					list.add(new CardInfo("list_item_project"));
					list.add(new CardInfo("list_item_distribution"));
					list.add(new CardInfo("list_item_visits"));
					list.add(new CardInfo("list_empty"));
					// // list.add(new CardInfo("card_item_arc"));
					// for (int i = 0; i < 100; i++) {
					// // list.add(new CardInfo("card_item" + i % 10));
					// list.add(new CardInfo("list_empty"));
					// }
					// lvMsgNotificationsEntryList.setMode(Mode.BOTH);
					if (null == adapter) {
						adapter = new CardListAdapter(CardMainActivity.this, list);
						lvMsgNotificationsEntryList.setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
					lvMsgNotificationsEntryList.onRefreshComplete();

					if (null != dialog) {
						dialog.cancel();
						dialog = null;
					}
					break;


				default:
					break;
			}
		};
	};

	private void initList() {

		lvMsgNotificationsEntryList = (PullToRefreshListView) findViewById(R.id.pulltorefreshlistview);
		lvMsgNotificationsEntryList.setMode(Mode.DISABLED);// both:
		// 刚开始没数据
		lvMsgNotificationsEntryList.setEmptyView(new EmptyView(this));
		// lvMsgNotificationsEntryList
		// .setOnRefreshListener(new OnRefreshListener2<ListView>() {
		//
		// @Override
		// public void onPullDownToRefresh(
		// PullToRefreshBase<ListView> refreshView) {
		// isRefreshing = true; // 下拉刷新标志
		// currentPage = 1;
		// isHasMore = true;
		// // doRequestReadedOrDeleted(read,
		// // REQUEST_MARK_READ_CODE, msgcategory, entryid, "");
		// doRequest(REQUEST_GET_DATA_CODE, "Y");
		// }
		//
		// @Override
		// public void onPullUpToRefresh(// 加载更多
		// PullToRefreshBase<ListView> refreshView) {
		// isRefreshing = false;
		// currentPage++;
		// doRequest(REQUEST_GET_MORE_DATA_CODE, "Y");
		// }
		//
		// });
		// lvMsgNotificationsEntryList
		// .setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
		//
		// @Override
		// public void onLastItemVisible() {
		// if (!isHasMore) {
		// // MyToast.showToast(getResources().getString(R.string.load_nodata);
		// }
		// }
		//
		// });

	}

	private void doRequest(String requestGetDataCode, String string) {
		if (requestGetDataCode.equals(REQUEST_GET_DATA_CODE)) {

		} else if (requestGetDataCode.equals(REQUEST_GET_MORE_DATA_CODE)) {

		} else if (requestGetDataCode.equals(REQUEST_MARK_READ_CODE)) {

		}
		initDate();

		// TODO Auto-generated method stub

	}

}
*/

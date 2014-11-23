package com.billme.widget;

import java.util.List;
import java.util.Map;

import android.content.Context;




public class RoundAdapte extends RoundListViewAdapter {


	public RoundAdapte(List<Map<String, Object>> data, Context context,
			List<String> headers) {
		super(data, context, headers);
	}

	@Override
	public int numberOfRowsInSection() {
		return 7;
	}

	@Override
	public int numberOfSectionsInRow(int row) {
		return row;
	}
}

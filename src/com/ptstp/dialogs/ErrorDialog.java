package com.ptstp.dialogs;

import com.ptstp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

/**
 * Custom error dialog for the app
 * @author Sanders
 */
public class ErrorDialog extends Builder {

	private Activity context;
	private boolean finishContextOnClose;
	
	public ErrorDialog(Activity context) {
		super(context);
		this.context = context;
		this.finishContextOnClose = false;
	}
	
	/**
	 * When the dialog closes, finish the parent activity
	 */
	public void finishActivityOnClose() {
		this.finishContextOnClose = true;
	}
	
	public AlertDialog create(String message) {
    	this.setMessage(message);
    	this.setTitle(R.string.defaultErrorHeader);
		return this.baseCreate();
	}
	
	private AlertDialog baseCreate() {
		this.setCancelable(false);
		this.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (finishContextOnClose) {
					context.finish();
				}
			}
		});
		return super.create();
	}
}

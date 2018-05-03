// Generated code from Butter Knife. Do not modify!
package com.example.cnwlc.app_memo.Memo;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.cnwlc.app_memo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ViewHolder_ViewBinding implements Unbinder {
  private ViewHolder target;

  @UiThread
  public ViewHolder_ViewBinding(ViewHolder target, View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.memo_item_textView_title, "field 'tvTitle'", TextView.class);
    target.tvImport = Utils.findRequiredViewAsType(source, R.id.memo_item_textView_import, "field 'tvImport'", TextView.class);
    target.tvDaily = Utils.findRequiredViewAsType(source, R.id.memo_item_textView_daily, "field 'tvDaily'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvImport = null;
    target.tvDaily = null;
  }
}

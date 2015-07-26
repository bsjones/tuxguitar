package org.herac.tuxguitar.app.action.impl.file;

import org.herac.tuxguitar.action.TGActionContext;
import org.herac.tuxguitar.action.TGActionManager;
import org.herac.tuxguitar.app.util.TGFileFormatUtils;
import org.herac.tuxguitar.editor.action.TGActionBase;
import org.herac.tuxguitar.util.TGContext;

public class TGSaveAsFileAction extends TGActionBase {
	
	public static final String NAME = "action.file.save-as";
	
	public TGSaveAsFileAction(TGContext context) {
		super(context, NAME);
	}
	
	protected void processAction(final TGActionContext context){
		final String fileName = TGFileFormatUtils.chooseFileName();
		if( fileName != null ){
			new Thread(new Runnable() {
				public void run() {
					context.setAttribute(TGWriteFileAction.ATTRIBUTE_FILE_NAME, fileName);
					
					TGActionManager tgActionManager = TGActionManager.getInstance(getContext());
					tgActionManager.execute(TGWriteFileAction.NAME, context);
				}
			}).start();
		}
	}
}
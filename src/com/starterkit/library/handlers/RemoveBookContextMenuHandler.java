package com.starterkit.library.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.starterkit.library.dialogs.BookEditDialog;
import com.starterkit.library.dialogs.BookRemoveConfirmatorDialog;
import com.starterkit.library.models.BookModel;

public class RemoveBookContextMenuHandler extends AbstractHandler {

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			for (Iterator<Object> iterator = strucSelection.iterator(); iterator.hasNext();) {
				Object element = iterator.next();
				BookRemoveConfirmatorDialog brcd = new BookRemoveConfirmatorDialog(null, (BookModel) element, null);
				brcd.open();
			}
		}
		return null;
	}

}

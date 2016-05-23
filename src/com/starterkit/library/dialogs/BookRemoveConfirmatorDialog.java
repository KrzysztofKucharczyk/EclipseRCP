package com.starterkit.library.dialogs;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.starterkit.library.models.BookModel;
import com.starterkit.library.models.BookModelProvider;

public class BookRemoveConfirmatorDialog extends TitleAreaDialog {

	private BookModel bookModel;

	public BookRemoveConfirmatorDialog(Shell parentShell, BookModel bookModel) {
		super(parentShell);
		this.bookModel = bookModel;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Are you sure you want to remove book " + bookModel.getTitle());
		setMessage("by " + bookModel.getAuthors() + "?");

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setToolTipText("Book details");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label lblCheckIfIt = new Label(container, SWT.NONE);
		lblCheckIfIt.setText("Check if it is a book you want to remove.");

		Label lbtTitle = new Label(container, SWT.NONE);
		lbtTitle.setText("Title: " + bookModel.getTitle());

		Label lbtAuthors = new Label(container, SWT.NONE);
		lbtAuthors.setText("Authors: " + bookModel.getAuthors());

		Label lbtStatus = new Label(container, SWT.NONE);
		lbtStatus.setText("Status: " + bookModel.getStatus());

		Label lbtGenre = new Label(container, SWT.NONE);
		lbtGenre.setText("Genre: " + bookModel.getGenre());

		Label lbtYear = new Label(container, SWT.NONE);
		lbtYear.setText("Year: " + bookModel.getYear());

		return area;
	}

	@Override
	protected void okPressed() {
		BookModelProvider.INSTANCE.deleteBook(bookModel.getId());
		BookModelProvider.INSTANCE.getBooksFromServer();
		super.okPressed();
	}

}

package com.starterkit.library.views;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.starterkit.library.models.BookModel;
import com.starterkit.library.models.BookModelProvider;

public class BookRemoveConfirmatorViewPart extends TitleAreaDialog {

	private BookModel bookModel;
	private TableViewer tableViewer;

	public BookRemoveConfirmatorViewPart(Shell parentShell, BookModel bookModel, TableViewer tableViewer) {
		super(parentShell);
		this.bookModel = bookModel;
		this.tableViewer = tableViewer;
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

	@Inject
	UISynchronize sync;

	@Override
	protected void okPressed() {

		// Job job = new Job("Delete operation in background") {
		//
		// @Override
		// protected IStatus run(IProgressMonitor monitor) {
	
		BookModelProvider.INSTANCE.deleteBook(bookModel.getId());
		// sync.asyncExec(new Runnable() {
		//
		// @Override
		// public void run() {
		tableViewer.setInput(BookModelProvider.INSTANCE.getBooks());
		//
		// }
		// });

		// return Status.OK_STATUS;
		// }
		// };
		// job.schedule();

		super.okPressed();
	}

}

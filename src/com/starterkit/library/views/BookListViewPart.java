package com.starterkit.library.views;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.starterkit.library.dialogs.BookAddDialog;
import com.starterkit.library.dialogs.BookDetailsDialog;
import com.starterkit.library.dialogs.BookEditDialog;
import com.starterkit.library.dialogs.BookRemoveConfirmatorDialog;
import com.starterkit.library.models.BookModel;
import com.starterkit.library.models.BookModelProvider;

import swing2swt.layout.BoxLayout;

@SuppressWarnings("deprecation")
public class BookListViewPart extends ViewPart {
	private TableViewer bookListTableViewer;
	private Text titleSearchTextField;
	private Text authorsSearchTextField;
	private Composite mainComposite;
	private Composite operationComposite;
	private GridData gd_mainComposite;
	private GridData gd_operationComposite;
	private GridData gd_text;
	private GridData tableGridData;
	private Label searchPanelLabel;
	private Label searchTitleLabel;
	private Label searchAuthorsLabel;
	private Button btnFilter;
	private Button btnAdd;
	private Button btnEdit;
	private Button btnDelete;
	private Button btnGetAllBooks;
	private MenuManager menuManager;
	private Menu menu;

	public BookListViewPart() {

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		mainComposite = new Composite(parent, SWT.NONE);

		searchPanelLabel = new Label(mainComposite, SWT.NONE);
		searchPanelLabel.setText("Search panel");
		new Label(mainComposite, SWT.NONE);
		new Label(mainComposite, SWT.NONE);
		new Label(mainComposite, SWT.NONE);
		initializeSearchComposite();

		new Label(parent, SWT.NONE);

		bookListTableViewer = new TableViewer(parent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, bookListTableViewer);
		Table table = bookListTableViewer.getTable();
		GridData gd_table = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_table.widthHint = 525;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		bookListTableViewer.setContentProvider(ArrayContentProvider.getInstance());

		initDataSource();
		getSite().setSelectionProvider(bookListTableViewer);

		operationComposite = new Composite(parent, SWT.NONE);
		initializeOperationComposite();

		initializeAddButton();
		initializeEditButton();
		initializeDeleteButton();
		btnGetAllBooks.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.getDisplay().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						BookModelProvider.INSTANCE.getBooksFromServer();
					}
				});
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		btnFilter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.getDisplay().asyncExec(new Runnable() {

					@Override
					public void run() {
						toggleButtons(true);
						buttonFilterAction();
					}
				});
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	
		
		new Label(parent, SWT.NONE);
		toggleButtons(true);
		tableGridData = new GridData();
		initializeTableGrid();

		initializeDoubleClickTableViewer();
		intializeSelectionForTableViewer();

		menuManager = new MenuManager();
		menu = menuManager.createContextMenu(bookListTableViewer.getTable());
		bookListTableViewer.getTable().setMenu(menu);
		getSite().registerContextMenu(menuManager, bookListTableViewer);

		// make the viewer selection available
		getSite().setSelectionProvider(bookListTableViewer);

	}
 
	private void intializeSelectionForTableViewer() {
		bookListTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				toggleButtons(false);
				IStructuredSelection selection = (IStructuredSelection) bookListTableViewer.getSelection();
				if (selection.isEmpty())
					return;

			}
		});
	}
	
	private void initializeDoubleClickTableViewer() {
		bookListTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) bookListTableViewer.getSelection();
				if (selection.isEmpty())
					return;
				BookDetailsDialog bookDetailsDialog = new BookDetailsDialog(null,
						(BookModel) selection.getFirstElement());
				bookDetailsDialog.open();
			}
		});
	}
	
	private void initializeAddButton() {
		btnAdd.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					BookAddDialog bookAddView = new BookAddDialog(null);
					bookAddView.open();
					break;
				}
			}
		});
	}
	
	private void initializeEditButton() {
		btnEdit.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					IStructuredSelection selection = (IStructuredSelection) bookListTableViewer.getSelection();
					BookEditDialog bookEditView = new BookEditDialog(null, (BookModel) selection.getFirstElement());
					bookEditView.open();
					break;
				}
			}
		});
	}
	
	private void initializeDeleteButton() {
		btnDelete.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					IStructuredSelection selection = (IStructuredSelection) bookListTableViewer.getSelection();

					BookRemoveConfirmatorDialog bookRemoveConfirmator = new BookRemoveConfirmatorDialog(null,
							(BookModel) selection.getFirstElement());
					bookRemoveConfirmator.open();
					toggleButtons(true);
					break;
				}
			}

		});
	}
	
	private DataBindingContext initDataSource() {
		DataBindingContext bindingContext = new DataBindingContext();
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(),
				BookModel.class, new String[] { "title", "authors", "status" });

		bookListTableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		bookListTableViewer.setContentProvider(listContentProvider);
		bookListTableViewer.setInput(BookModelProvider.INSTANCE.getBooks());

		return bindingContext;

	}
	
	private void toggleButtons(boolean isNoRowSelected) {
		if (isNoRowSelected) {
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
		} else {
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);
		}
	}

	private void initializeSearchComposite() {
		gd_mainComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_mainComposite.widthHint = 548;
		gd_mainComposite.heightHint = 64;
		mainComposite.setLayoutData(gd_mainComposite);
		mainComposite.setLayout(new GridLayout(5, false));
		new Label(mainComposite, SWT.NONE);
		btnFilter = new Button(mainComposite, SWT.NONE);
		btnFilter.setText("Filter");
		searchTitleLabel = new Label(mainComposite, SWT.NONE);
		searchTitleLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		searchTitleLabel.setText("Title:");

		titleSearchTextField = new Text(mainComposite, SWT.BORDER);
		gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text.widthHint = 64;
		titleSearchTextField.setLayoutData(gd_text);

		searchAuthorsLabel = new Label(mainComposite, SWT.NONE);
		searchAuthorsLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		searchAuthorsLabel.setText("Authors:");

		authorsSearchTextField = new Text(mainComposite, SWT.BORDER);
		authorsSearchTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	private void initializeOperationComposite() {
		operationComposite.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		gd_operationComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_operationComposite.heightHint = 104;
		gd_operationComposite.widthHint = 528;
		operationComposite.setLayoutData(gd_operationComposite);

		btnAdd = new Button(operationComposite, SWT.NONE);
		btnAdd.setText("Add");

		btnEdit = new Button(operationComposite, SWT.NONE);
		btnEdit.setText("Edit");

		btnDelete = new Button(operationComposite, SWT.NONE);
		btnDelete.setText("Delete");

		btnGetAllBooks = new Button(operationComposite, SWT.NONE);
		btnGetAllBooks.setText("Get all books");
	}

	private void initializeTableGrid() {
		tableGridData.verticalAlignment = GridData.BEGINNING;
		tableGridData.horizontalSpan = 2;
		tableGridData.grabExcessHorizontalSpace = true;
		tableGridData.grabExcessVerticalSpace = true;
		tableGridData.horizontalAlignment = GridData.FILL_HORIZONTAL;
	}

	private void buttonFilterAction() {
		if (titleSearchTextField.getText().length() > 0 || authorsSearchTextField.getText().length() > 0)
			BookModelProvider.INSTANCE.getBooksFromServer(titleSearchTextField.getText(), authorsSearchTextField.getText());
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Title", "Authors", "Status", "Genre", "Year" };
		int[] bounds = { 200, 200, 100, 200, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				BookModel book = (BookModel) element;
				return book.getTitle();
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				BookModel book = (BookModel) element;
				return book.getAuthors();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				BookModel book = (BookModel) element;
				return book.getStatus();
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(bookListTableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	@Override
	public void setFocus() {
		bookListTableViewer.getControl().setFocus();
	}

}

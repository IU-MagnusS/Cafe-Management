import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ProductService } from 'src/app/services/product.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { ProductComponent } from '../dialog/product/product.component';

@Component({
  selector: 'app-manage-product',
  templateUrl: './manage-product.component.html',
  styleUrls: ['./manage-product.component.scss']
})
export class ManageProductComponent implements OnInit {

  displayedColumns: string[] = ['name', 'categoryName', 'description', 'price', 'edit'];
  dataSource: any;
  length: any;
  responseMessage: any;

  constructor(
    private productService: ProductService,
    private ngxService: NgxUiLoaderService,
    private dialog: MatDialog,
    private snackbarService: SnackbarService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
  }

  tableData() {
    this.productService.getProducts().subscribe(
      (response: any) => {
        this.ngxService.stop();
        this.dataSource = new MatTableDataSource(response);
      },
      (error: any) => {
        this.ngxService.stop();
        console.log(error.error?.message);
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  handleAddAction() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'Add'
    };
    dialogConfig.width = "850px";
    const dialogRef = this.dialog.open(ProductComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
    const sub = dialogRef.componentInstance.onAddProduct.subscribe((response) => {
      this.tableData();
    });
  }
  

  handleEditAction(value: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'Edit',
      data: value
    };
    dialogConfig.width = "850px";
    const dialogRef = this.dialog.open(ProductComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onEditProduct.subscribe((response: any) => {
      this.tableData();
    });
  }

  handleDeleteAction(value: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: 'Delete ' + value.name + ' product',
      confirmation: true
    };
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);
    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.ngxService.start();
        this.deleteProduct(value.id);
        dialogRef.close();
      }
    });
  }

  deleteProduct(id: any) {
    this.productService.delete(id).subscribe(
      () => {
        this.ngxService.stop();
        this.snackbarService.openSnackBar('Product deleted successfully.', 'success');
        this.tableData();
      },
      (error: any) => {
        this.ngxService.stop();
        console.log(error);
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    );
  }

  onChange(status: any, id: any) {
    this.ngxService.start();
    var data = {
      status: status.toString(),
      id: id
    };

    this.productService.updateStatus(data).subscribe(
      (response: any) => {
        this.ngxService.stop();
        this.responseMessage = response?.message;
        this.snackbarService.openSnackBar(this.responseMessage, "success");
      },
      (error: any) => {
        this.ngxService.stop();
        console.log(error);
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    );
  }
}

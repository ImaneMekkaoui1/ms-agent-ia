
import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AsyncPipe } from '@angular/common';
import { catchError, map, Observable, of } from 'rxjs';

import { Client, ClientListState } from '../model/client.model';
import { RequestStatus } from '../model/account.model';
import { LoadingService } from '../services/loading.service';

@Component({
  selector: 'app-clients',
  imports: [AsyncPipe],
  templateUrl: './clients.component.html',
  styleUrl: './clients.component.css'
})
export class ClientsComponent {
  private http = inject(HttpClient);
  public loadService = inject(LoadingService);

  clients$: Observable<ClientListState> = this.http
    .get<Client[]>('http://localhost:9999/customers')
    .pipe(
      map(resp => ({
        clients: resp,
        status: RequestStatus.SUCCESS
      })),
      catchError(err =>
        of({
          status: RequestStatus.ERROR,
          errorMessage: err.message || err.statusText
        })
      )
    );

  protected readonly RequestStatus = RequestStatus;
}

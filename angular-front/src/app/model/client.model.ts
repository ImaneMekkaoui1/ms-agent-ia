
export interface Client {
  id: number;
  name?: string;
  email?: string;
}

export enum RequestStatus {
  SUCCESS,
  ERROR
}

export interface ClientListState {
  clients?: Client[];
  status?: RequestStatus;
  errorMessage?: string;
}

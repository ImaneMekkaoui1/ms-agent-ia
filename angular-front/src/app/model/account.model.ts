export interface Account{
  id: string;
  balance?:number,
  createdAt?:string,
  type?:string,
  customerId?:number
}
export enum RequestStatus{
  SUCCESS,
  ERROR,
}
export interface AccountListState{
  accounts?:Account[],
  status? : RequestStatus,
  errorMessage?: string
}


import { Routes } from '@angular/router';
import {AccountsComponent} from './accounts/accounts.component';
import {BotUiComponent} from './bot-ui/bot-ui.component';
import {ClientsComponent} from './clients/clients.component';

export const routes: Routes = [
  {path:"accounts", component:AccountsComponent},
  {path:"bot", component:BotUiComponent},
  {path:"clients", component:ClientsComponent}
];

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CourseListComponent } from './components/course-list/course-list.component';
import { SubscriptionListComponent } from './components/subscription-list/subscription-list.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthGuard } from './auth.guard';
import { SettingsComponent } from './components/settings/settings.component';
import { CourseCreateComponent } from './components/course-create/course-create.component';
import { AdminCourseApprovalComponent } from './components/admin-course-approval/admin-course-approval.component';
import { RDFComponent } from './components/rdf/rdf.component'; // Importar el nuevo componente

const routes: Routes = [
  { path: 'courses', component: CourseListComponent, canActivate: [AuthGuard] },
  { path: 'users', component: UserListComponent, canActivate: [AuthGuard] },
  { path: 'subscriptions', component: SubscriptionListComponent, canActivate: [AuthGuard] },
  { path: 'settings', component: SettingsComponent, canActivate: [AuthGuard] },
  { path: 'create-course', component: CourseCreateComponent, canActivate: [AuthGuard] },
  { path: 'admin/approve-courses', component: AdminCourseApprovalComponent, canActivate: [AuthGuard] },
  { path: 'rdf', component: RDFComponent, canActivate: [AuthGuard] }, // Añadir la ruta para el nuevo componente
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

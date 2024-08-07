import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  displayedColumns: string[] = ['id', 'email', 'first_name', 'last_name', 'user_type'];
  errorMessage: string | null = null;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getUsers().subscribe((data: User[]) => {
      console.log(data);
      this.users = data;
    });
  }


  deleteUser(userId: number): void {
    this.userService.deleteUser(userId).subscribe(() => {
      this.users = this.users.filter(user => user.id !== userId);
      alert('Curso eliminado exitosamente');
    }, error => {
      this.errorMessage = error.message || 'Error al eliminar usuario no se puede eliminar un usuario suscrito a un curso';
      console.log('Error al eliminar usuario no se puede eliminar un usuario suscrito a un curso', error);
    }
);
  }

}

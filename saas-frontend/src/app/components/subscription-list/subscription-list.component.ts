import { Component, OnInit } from '@angular/core';
import { SubscriptionService } from '../../services/subscription.service';
import { AuthService } from '../../services/auth.service';
import { Subscription } from '../../models/subscription.model';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-subscription-list',
  templateUrl: './subscription-list.component.html',
  styleUrls: ['./subscription-list.component.scss']
})
export class SubscriptionListComponent implements OnInit {
  subscriptions: Subscription[] = [];
  currentUser: User | null = null;
  isCreator: boolean = false;
  errorMessage: string | null = null;

  constructor(
    private subscriptionService: SubscriptionService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.authService.getUserProfile().subscribe(user => {
      this.currentUser = user;
      this.isCreator = user.userType === 'CREATOR';
      if (this.isCreator) {
        this.loadCreatorSubscriptions(this.currentUser?.id ?? 0);
      } else {
        this.loadSubscriptions();
      }
    });
  }

  loadSubscriptions(): void {
    this.subscriptionService.getSubscriptions().subscribe((subscriptions: Subscription[]) => {
      this.subscriptions = subscriptions;
    });
  }

  loadCreatorSubscriptions(creatorId: number): void {
    this.subscriptionService.getSubscriptionsByCreator(creatorId).subscribe((subscriptions: Subscription[]) => {
      this.subscriptions = subscriptions;
    });
  }

  deleteSubscription(id: number): void {
    this.subscriptionService.deleteSubscription(id).subscribe(() => {
      this.subscriptions = this.subscriptions.filter(subscription => subscription.id !== id);
    }, error => {
      console.error('Error al eliminar la suscripción', error);
    });
  }

  subscribe(courseId: number): void {
      this.errorMessage = null;  // Reset error message
      this.subscriptionService.createSubscription(courseId, this.currentUser?.id ?? 0).subscribe(
        () => {
          alert('Suscripción exitosa');
          this.loadSubscriptions();
        },
        error => {
          this.errorMessage = error.error.message || 'Error al suscribirse';
          console.error('Error al suscribirse', error);
        }
      );
    }
}

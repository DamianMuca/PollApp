import {Injectable} from '@angular/core';
import {Poll} from './poll';
import {Observable, Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {REST_API_URL} from '../../../common';
import {UserAnswer} from './user.answer';
import {UserWhoAnsweredPoll} from './userWhoAnsweredPoll';
import {User} from '../user/user';

@Injectable({
  providedIn: 'root'
})
export class PollService {
  public pollsList$: Subject<Poll[]> = new Subject<Poll[]>();
  public myPollsList$: Subject<Poll[]> = new Subject<Poll[]>();
  public pollDetails$: Subject<Poll> = new Subject<Poll>();
  public usersWhoAnsweredPoll$: Subject<User[]> = new Subject<User[]>();
  public usersWhoDidNotAnswerPoll$: Subject<User[]> = new Subject<User[]>();

  constructor(private http: HttpClient) {
  }

  public listAllPolls() {
    this.http.get<Poll[]>(`${REST_API_URL}listPolls`).subscribe((polls: Poll[]) => {
      this.pollsList$.next(polls);
    });
  }

  public listMyPolls() {
    this.http.get<Poll[]>(`${REST_API_URL}listMyPolls`).subscribe((polls: Poll[]) => {
      this.myPollsList$.next(polls);
    });
  }

  public createPoll(poll: Poll): Observable<boolean> {
    return this.http.post<boolean>(`${REST_API_URL}createPoll`, poll);
  }

  public getPollDetails(pollId: number) {
    return this.http.get<Poll>(`${REST_API_URL}getPollDetails/${pollId}`).subscribe((pollDetails: Poll) => {
      this.pollDetails$.next(pollDetails);
    });
  }

  public saveAnswers(userAnswers: UserAnswer[]): Observable<number> {
    return this.http.post<number>(`${REST_API_URL}saveUserAnswers`, userAnswers);
  }

  public verifyPollAnswers(userWhoAnsweredPoll: UserWhoAnsweredPoll): Observable<boolean> {
    return this.http.post<boolean>(`${REST_API_URL}verifyPollAnswers`, userWhoAnsweredPoll);
  }

  getUsersWhoAnsweredToPoll(pollId: number) {
    return this.http.post<User[]>(`${REST_API_URL}getUsersWhoAnsweredToPoll`, pollId).subscribe((usersAnsweredPoll) => {
      this.usersWhoAnsweredPoll$.next(usersAnsweredPoll);
    });
  }

  getUsersWhoDidNotAnswerToPoll(pollId: number) {
    return this.http.post<User[]>(`${REST_API_URL}getUsersWhoDidNotAnswerToPoll`, pollId).subscribe((usersDidNotAnswerPoll) => {
      this.usersWhoDidNotAnswerPoll$.next(usersDidNotAnswerPoll);
    });
  }
}

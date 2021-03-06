import {Component, OnInit} from '@angular/core';
import {Poll} from '../../model/poll/poll';
import {UserAnswer} from '../../model/poll/user.answer';
import {ActivatedRoute} from '@angular/router';
import {PollService} from '../../model/poll/poll.service';
import {map} from 'rxjs/operators';
import {Subject} from 'rxjs';

@Component({
  selector: 'app-view-my-answers',
  templateUrl: './view-my-answers.component.html',
  styleUrls: ['./view-my-answers.component.scss']
})
export class ViewMyAnswersComponent implements OnInit {
  public poll: Poll = new Poll();
  userAnswers: UserAnswer[] = [];
  loading: boolean;
  pollId$: Subject<number> = new Subject<number>();

  constructor(private activatedRoute: ActivatedRoute,
              private pollService: PollService) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap
    .pipe(map(() => window.history.state))
    .subscribe(state => {
      this.pollService.getPollDetails(state.poll.pollId);
      this.pollService.pollDetails$.subscribe((poll: Poll) => {
        this.poll = poll;
        this.pollId$.next(poll.pollId);
        this.userAnswers = new Array(this.poll.questions.length);
        for (let i = 0; i < this.poll.questions.length; ++i) {
          this.userAnswers[i] = {
            questionId: this.poll.questions[i].questionId,
            answerChosen: -1,
          };
        }
      });
    });
  }
}

import { Component, Input } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { ICONS } from './icons';

@Component({
  selector: 'app-icon',
  standalone: true,
  template: `<span class="icon-wrapper" [style.width.px]="size" [style.height.px]="size" [innerHTML]="svg"></span>`,
  styles: [`
    :host { display: inline-flex; align-items: center; justify-content: center; }
    .icon-wrapper { display: inline-flex; align-items: center; justify-content: center; }
    .icon-wrapper :host-context(.icon-wrapper) svg,
    :host ::ng-deep svg { width: 100%; height: 100%; }
  `]
})
export class IconComponent {
  @Input() name: string = '';
  @Input() size: number = 20;

  svg: SafeHtml = '';

  constructor(private sanitizer: DomSanitizer) {}

  ngOnChanges(): void {
    const raw = ICONS[this.name] || '';
    this.svg = this.sanitizer.bypassSecurityTrustHtml(raw);
  }
}

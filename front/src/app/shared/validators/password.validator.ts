import { AbstractControl } from '@angular/forms';

export function PasswordValidator(
  control: AbstractControl
): { [key: string]: boolean } | null {
  if (!control.value) {
    return null;
  }

  const hasLowerCase = /[a-z]/.test(control.value);
  const hasUpperCase = /[A-Z]/.test(control.value);
  const hasNumber = /\d/.test(control.value);
  const hasSpecialCharacter = /[@$!%*?&#,;:]/.test(control.value);

  const isValid =
    control.value.length >= 8 &&
    hasLowerCase &&
    hasUpperCase &&
    hasNumber &&
    hasSpecialCharacter;

  return isValid ? null : { invalidPassword: true };
}

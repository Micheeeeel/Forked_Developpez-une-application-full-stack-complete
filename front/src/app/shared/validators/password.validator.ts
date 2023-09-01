import { AbstractControl } from '@angular/forms';

export function PasswordValidator(
  control: AbstractControl
): { [key: string]: boolean } | null {
  if (!control.value) {
    return null;
  }

  const hasLowerCase = /[a-z]/.test(control.value); // test if the password has at least one lowercase letter
  const hasUpperCase = /[A-Z]/.test(control.value); // test if the password has at least one uppercase letter
  const hasNumber = /\d/.test(control.value); // test if the password has at least one number
  const hasSpecialCharacter = /[@$!%*?&#,;:]/.test(control.value); // test if the password has at least one special character

  const isValid =
    control.value.length >= 8 &&
    hasLowerCase &&
    hasUpperCase &&
    hasNumber &&
    hasSpecialCharacter;

  return isValid ? null : { invalidPassword: true };
}

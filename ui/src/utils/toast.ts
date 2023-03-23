import { Slide, toast } from 'react-toastify';

export const showSuccessToast = (message: string, toastId: string) => {
  toast.success(message, {
    position: toast.POSITION.BOTTOM_RIGHT,
    transition: Slide,
    toastId,
    delay: 3000,
  });
};

export const showErrorToast = (message: string, toastId: string) => {
  toast.error(message, {
    position: toast.POSITION.BOTTOM_RIGHT,
    transition: Slide,
    toastId,
    delay: 3000,
  });
};

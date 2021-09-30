import { notification } from 'antd';

interface NotificationConfig {
  title: string;
  content?: string;
  duration?: number;
  type: 'info' | 'warning' | 'error' | 'success';
};

export function showNotification(config: NotificationConfig) {
  const { type, title, content, duration } = config;
  notification[type]({
    message: title,
    description: content,
    duration: duration ?? 4
  });
}
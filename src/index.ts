import * as android from './android';


export interface Intent {
  /** the android intent action. can be a Intent.X constant like "ACTION_SEND" */
  action: string;
  /** intent.setType */
  type?: string;
  /** intent.setPackage */
  package?: string;
  /** intent.setData(Uri) */
  data?: string;
  /** intent.setExtra(Intent.EXTRA_STREAM, Uri) */
  extra_stream?: string;
  /** intent.setExtra(key, value) */
  extras?: { [key: string]: string | number | boolean };
}


export class AndroidActivity {
  /**
   * native android functions. use with caution
   */
  public static readonly android = android;

  /**
   * check if android package is installed
   */
  public static async isPackageInstalled(packageId: string): Promise<boolean> {
    if (this.android.Module) {
      return await this.android.Module.isPackageInstalled(packageId);
    } else {
      throw new Error('platform not supported');
    }
  }

  /**
   * start android activity
   */
  public static async startActivity(args: Intent): Promise<void> {
    if (this.android.Module) {
      return await this.android.Module.startActivity(args);
    } else {
      throw new Error('platform not supported');
    }
  }

  /**
   * start android activity for result
   */
  public static async startActivityForResult(args: Intent): Promise<{ result: number; }> {
    if (this.android.Module) {
      return await this.android.Module.startActivityForResult(args);
    } else {
      throw new Error('platform not supported');
    }
  }

}

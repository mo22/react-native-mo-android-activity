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
    extras?: {
        [key: string]: string | number | boolean;
    };
}
export declare class AndroidActivity {
    /**
     * native android functions. use with caution
     */
    static readonly android: typeof android;
    /**
     * check if android package is installed
     */
    static isPackageInstalled(packageId: string): Promise<boolean>;
    /**
     * start android activity
     */
    static startActivity(args: Intent): Promise<void>;
    /**
     * start android activity for result
     */
    static startActivityForResult(args: Intent): Promise<{
        result: number;
    }>;
}
